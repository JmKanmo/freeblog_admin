class NoticeController extends UtilController {
    constructor() {
        super();
        this.noticeWriteButton = document.getElementById("notice_write_button");
        this.noticeListButton = document.getElementById("notice_list_button");

        // page size
        this.likeRecordSize = 5;
        this.likePageSize = 5;
    }

    initNoticeController() {
        this.initEventListener();
    }

    initEventListener() {
        this.noticeWriteButton.addEventListener("click", evt => {
            this.openPopUp(1080, 500, "/notice/write", 'popup');
        });

        this.noticeListButton.addEventListener("click", evt => {
            this.#requestNotice();
        });
    }

    #requestNotice(url, page) {
        const xhr = new XMLHttpRequest();

        if (!page) {
            // default 페이지 요청
            const queryParam = this.getQueryParam(page, this.likeRecordSize, this.likePageSize);
            //  TODO
        } else {
            // TODO
            const queryParam = null;
        }
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const noticeController = new NoticeController();
    noticeController.initNoticeController();
});