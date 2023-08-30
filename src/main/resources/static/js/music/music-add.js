class MusicAddController extends UtilController {
    constructor() {
        super();
    }

    initMusicAddController() {
        this.initEventListener();
    }

    initEventListener() {

    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicAddController = new MusicAddController();
    musicAddController.initMusicAddController();
});