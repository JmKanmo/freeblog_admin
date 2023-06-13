class NoticeController extends UtilController {
    constructor() {
        super();
        this.noticeWriteButton = document.getElementById("notice_write_button");
        this.noticeListButton = document.getElementById("notice_list_button");
    }

    initNoticeController() {
        this.initEventListener();
    }

    initEventListener() {
        this.noticeWriteButton.addEventListener("click", evt => {
            this.openPopUp(1080, 500, "/notice/write", 'popup');
        });

        this.noticeListButton.addEventListener("click", evt => {
            this.openPopUp(1080, 500, "/notice/list", 'popup');
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const noticeController = new NoticeController();
    noticeController.initNoticeController();
});