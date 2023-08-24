class MusicCategoryAddController extends UtilController {
    constructor() {
        super();
        this.musicCategoryAddForm = document.getElementById("musicCategoryAddForm");
        this.musicCategoryAddSubmitButton = document.getElementById("musicCategoryAddButton");
    }

    initMusicCategoryAddController() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicCategoryAddSubmitButton.addEventListener("click", evt => {
            if (confirm("뮤직 카테고리를 등록 하겠습니까?")) {
                this.musicCategoryAddForm.submit();
            }
        });
    }
}


// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicCategoryAddController = new MusicCategoryAddController();
    musicCategoryAddController.initMusicCategoryAddController();
});