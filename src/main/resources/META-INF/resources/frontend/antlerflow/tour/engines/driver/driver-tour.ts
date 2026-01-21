import {Driver, driver} from 'driver.js';
import 'driver.js/dist/driver.css';

function toDriverStep(step: any, showButtons: string[] = []) {
    const element = step.attachTo;
    const position = (step.position || 'auto');
    const align = (step.align || 'center');
    return {
        id: step.id,
        element,
        popover: {
            title: step.title || '',
            description: step.content,
            side: position,
            align,
            showButtons
        }
    };
}

function toActionFn(driver: Driver, btn: { type: string }) {
    const type = btn.type?.toLowerCase();
    switch (type) {
        case 'next':
            return () => driver.moveNext();
        case 'previous':
            return () => driver.movePrevious();
        case 'cancel':
            return () => driver.destroy();
        default:
            return undefined;
    }
}

export function startDriverTour(steps: any[], options: any = {}): () => void {
    const stepButtonsMap = steps.map(step => {
        return {stepId: step.id, buttons: step.buttons};
    });
    const showButtons: string[] = [];
    if (options.showCancelButton) showButtons.push('close');
    let canceled = false;
    const d = driver({
        showProgress: true,
        allowClose: options?.allowClose || false,
        animate: true,
        onPopoverRender: (popover, {state, driver}) => {
            // @ts-ignore
            const stepId = state.activeStep.id;
            const stepButtons = stepButtonsMap.find(step => step.stepId === stepId)?.buttons || [];
            popover.footerButtons.replaceChildren();
            // @ts-ignore
            stepButtons.map(btn => {
                const button = document.createElement("button");
                button.className = `driver-button${btn.secondary ? ' secondary' : ''}`;
                button.innerText = btn.label;
                button.addEventListener('click', () => toActionFn(driver, btn)?.call(driver));
                popover.footerButtons.appendChild(button);
            });
        },
        onDestroyed: () => {
            if (canceled) return;
            (window as any).VaadinTour._emit('complete');
        },
        onCloseClick: () => {
            canceled = true;
            (window as any).VaadinTour._emit('cancel');
            d.destroy();
        },
        ...options,
        steps: steps.map(step => toDriverStep(step, showButtons))
    });

    d.drive();
    return d.destroy;
}