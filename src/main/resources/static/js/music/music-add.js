class MusicAddController extends UtilController {
    constructor() {
        super();
        this.musicAddForm = document.getElementById("musicAddForm");
        this.musicAddButton = document.getElementById("musicAddButton");
    }

    initMusicAddController() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicAddButton.addEventListener("click", evt => {
            if (confirm("뮤직 정보를 추가 하겠습니까?")) {

                this.musicAddForm.submit();
            }
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicAddController = new MusicAddController();
    musicAddController.initMusicAddController();
});