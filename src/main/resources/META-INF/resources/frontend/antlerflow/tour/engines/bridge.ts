(() => {
    const VT = (window as any).VaadinTour;
    const origStart = VT.start.bind(VT);
    let cancel: () => void;
    VT.start = async (engineId: string, steps: any[], options: any = {}) => {
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
    const origCancel = VT.cancel.bind(VT);
    VT.cancel = () => {
        if (cancel) {
            cancel();
        } else {
            origCancel();
        }
    };
})();
