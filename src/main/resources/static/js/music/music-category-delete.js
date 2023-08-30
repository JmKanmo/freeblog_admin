class MusicCategoryDeleteController extends UtilController {
    constructor() {
        super();
        this.musicCategoryDeleteForm = document.getElementById("musicCategoryDeleteForm");
        this.musicCategoryList = document.getElementById("musicCategoryList");
        this.musicCategoryHiddenSelectId = document.getElementById("music_category_hidden_select_id");
        this.musicCategoryDeleteButton = document.getElementById("musicCategoryDeleteButton");
    }

    initMusicCategoryDeleteController() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicCategoryDeleteButton.addEventListener("click", evt => {
            if (confirm("뮤직 카테고리를 삭제하겠습니까?")) {
                this.musicCategoryDeleteForm.submit();
            }
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicCategoryDeleteController = new MusicCategoryDeleteController();
    musicCategoryDeleteController.initMusicCategoryDeleteController();
});