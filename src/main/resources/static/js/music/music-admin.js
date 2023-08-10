class MusicController extends UtilController {
    constructor() {
        super();
    }

    initMusicController() {
        this.initEventListener();
    }

    initEventListener() {
        // TODO
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicController = new MusicController();
    musicController.initMusicController();
});