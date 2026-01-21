(() => {
    const AT = (window as any).AntlerTour;
    const origStart = AT.start.bind(AT);
    let cancel: () => void;
    AT.start = async (engineId: string, steps: any[], options: any = {}) => {
        if (engineId === 'driver') {
            const mod = await import('./driver/driver-tour');
            cancel = mod.startDriverTour(steps, options);
            return cancel;
        } else if (engineId === 'shepherd') {
            const mod = await import('./shepherd/shepherd-tour');
            cancel = mod.startShepherdTour(steps, options);
            return cancel;
        }

        return origStart(engineId, steps, options);
    };
    const origCancel = AT.cancel.bind(AT);
    AT.cancel = () => {
        if (cancel) {
            cancel();
        } else {
            origCancel();
        }
    };
})();
