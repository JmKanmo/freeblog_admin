class NoticeListController extends UtilController {
    constructor() {
        super();
        // page size
        this.likeRecordSize = 5;
        this.likePageSize = 5;
    }

    initNoticeListController() {

    }
}

document.addEventListener("DOMContentLoaded", () => {
    const noticeListController = new NoticeListController();
    noticeListController.initNoticeListController();
});