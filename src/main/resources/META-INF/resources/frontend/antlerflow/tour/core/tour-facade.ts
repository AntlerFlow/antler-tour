(() => {
    type TourEvent = 'complete' | 'cancel';
    const ERROR_PREFIX = 'No engine registered for: ';
    const listeners = new Map<TourEvent, Set<() => void>>();

    if (!(window as any).VaadinTour) {
        (window as any).VaadinTour = {
            async start(engineId: string, _steps: any[], _options: any = {}) {
                throw new Error(`${ERROR_PREFIX}${engineId}`);
            },
            async cancel() {
                console.warn("No active tour to cancel.");
            },
            on(type: TourEvent, cb: () => void) {
                const eventListeners = listeners.get(type) || new Set();
                if (!listeners.has(type)) listeners.set(type, eventListeners);
                eventListeners.add(cb);
            },
            _emit(type: TourEvent) {
                listeners.get(type)?.forEach(cb => {
                    try {
                        cb();
                    } catch (e) {
                        console.error(e);
                    }
                });
            }
        };
    }
})();
