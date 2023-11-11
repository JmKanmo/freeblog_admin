class NoticeUpdateController extends UtilController {
    constructor() {
        super();
        this.noticeUpdateForm = document.getElementById("notice_update_form");
        this.noticeTitle = document.getElementById("notice_title");
        this.noticeUpdateEditor = this.getQuillEditor("notice_update_editor");
        this.viewSourceBtn = document.getElementById("view-editor-source");

        this.hiddenNoticeTitle = document.getElementById("hidden_notice_title");
        this.hiddenNoticeContent = document.getElementById("hidden_notice_content");
        this.hiddenNoticeSummary = document.getElementById("hidden_notice_summary");
        this.hiddenUploadKey = document.getElementById("hidden_upload_key");

        this.isSubmitFlag = false;
    }

    initNoticeUpdateController() {
        this.initNoticeContent();
        this.initEventListener();
    }

    initNoticeContent() {
        this.noticeUpdateEditor.root.innerHTML = this.compressContent(document.getElementById("hidden_notice_content").value, false);
    }

    initEventListener() {
        this.viewSourceBtn.addEventListener("click", evt => {
            const wnd = this.openPopUp(500, 500, "HTML Source View", "new window");

            wnd.document.write(
                `<html>
                    <head>
                    <title>View HTML Source</title>
                    <style>
                        .code_view_title {
                            text-align:center;
                            color: #607d8b;
                            font-size: 18px;
                        }
                        
                         .code_container { 
                             width: 485px;
                             height: 455px;
                             text-align: left; 
                             white-space: pre-line; 
                             background-color: #f1f8e9; 
                             border: 1px solid #eeeeee;
                             color: #9c27b0; 
                             padding:10px;
                          }
                          
                         .code_container:before { 
                             content: ""; 
                             display: block; 
                             height: 1em; 
                             margin: 0 -5px -2em -5px; 
                        }
                    </style> 
                    </head>
                   
                    <body>                   
                        <h1 class="code_view_title">HTML Source View</h1>
                            <textarea class="code_container">
                               ${this.noticeUpdateEditor.root.innerHTML}
                            </textarea>
                    </body>
                </html>`);
        });

        this.noticeUpdateForm.addEventListener("submit", evt => {
            if (this.isSubmitFlag === true) {
                this.showToastMessage("게시글을 발행 중입니다.");
                return;
            }
            evt.preventDefault();

            if (confirm('게시글을 발행하겟습니까?')) {
                if (this.checkNoticeUpdateInfo()) {
                    this.showToastMessage("빈칸,공백만 포함 된 정보는 유효하지 않습니다.");
                    this.isSubmitFlag = false;
                    return false;
                } else {
                    const compressedContent = this.compressContent(this.noticeUpdateEditor.root.innerHTML, true);

                    if (this.checkPostContentSize(compressedContent, this.MAX_POST_CONTENT_SIZE)) {
                        this.showToastMessage("공지사항 본문 크기가 허용 범위를 초과하였습니다.");
                        return;
                    }

                    this.hiddenNoticeContent.value = compressedContent;
                    this.hiddenNoticeSummary.value = this.replaceAndSubHTMlTag(this.noticeUpdateEditor.root.innerHTML, 200);
                    this.hiddenNoticeTitle.value = this.noticeTitle.value;
                    this.hiddenUploadKey.value = document.getElementById("upload_key").value;
                    this.noticeUpdateForm.submit();
                    this.isSubmitFlag = true;
                    return true;
                }
            }
        });
    }

    checkNoticeUpdateInfo() {
        if (!this.noticeTitle.value ||
            ((this.noticeUpdateEditor.root.innerText === null || this.getRemoveSpaceStr(this.noticeUpdateEditor.root.innerHTML) === "<p></p>") ||
                (this.noticeUpdateEditor.root.innerText.replace(/ /g, "") === null || this.getRemoveSpaceStr(this.noticeUpdateEditor.root.innerHTML) === "<p></p>")) ||
            (!this.noticeUpdateEditor.root.innerText.replace(/ /g, "") === null || this.getRemoveSpaceStr(this.noticeUpdateEditor.root.innerHTML) === "<p></p>")) {
            return true;
        } else {
            return false;
        }
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const noticeUpdateController = new NoticeUpdateController();
    noticeUpdateController.initNoticeUpdateController();
});