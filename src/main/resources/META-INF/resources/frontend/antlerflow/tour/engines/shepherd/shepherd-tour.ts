import Shepherd, {Tour} from 'shepherd.js';
import 'shepherd.js/dist/css/shepherd.css';

/**
 * Map string actions ("next","back","cancel"/"done"/"finish") to functions.
 * This prevents Shepherd internally calling `.bind` on a non-function.
 */
function toActionFn(tour: Tour, btn: { type: string }) {
    const type = btn.type?.toLowerCase();
    switch (type) {
        case 'next':
            return () => tour.next();
        case 'previous':
            return () => tour.back();
        case 'cancel':
            return () => tour.cancel();
        default:
            return undefined;
    }
}

function normalizeButtons(tour: Tour, buttons: { label: string, type: string }[] = []): {
    label: string,
    action: () => void
}[] {
    return buttons.map((btn): any => {
        return {...btn, text: btn.label, action: toActionFn(tour, btn)};
    });
}

export function startShepherdTour(steps: any[], options: any = {}): () => void {
    const tour = new Shepherd.Tour({
        useModalOverlay: true,
        exitOnEsc: options?.allowClose || false,
        exitOnOverlayClick: options?.allowClose || false,
        keyboardNavigation: true,
        confirmCancel: false,
        defaultStepOptions: {
            scrollTo: {behavior: 'smooth', block: 'center'},
            canClickTarget: true,
            cancelIcon: {enabled: options?.showCancelButton || false},
            arrow: true,
            classes: 'shepherd-step'
        },
        ...options
    });

    steps.forEach((raw) => {
        const step: any = {
            ...raw,
            text: raw.content || '',
            attachTo: {element: raw.attachTo, on: raw.position || 'auto'},
        };
        step.buttons = normalizeButtons(tour, step.buttons);
        tour.addStep(step);
    });

    // Forward lifecycle back to Vaadin
    tour.on('complete', () => (window as any).VaadinTour._emit('complete'));
    tour.on('cancel', () => (window as any).VaadinTour._emit('cancel'));

    tour.start();
    return tour.cancel;
}
