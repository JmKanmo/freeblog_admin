class NoticeWriteController extends UtilController {
    constructor() {
        super();
        this.noticeWriteRegisterButton = document.getElementById("notice_write_register_button");
        this.noticeWriteForm = document.getElementById("notice_write_form");
        this.noticeTitle = document.getElementById("notice_title");
        this.hiddenNoticeTitile = document.getElementById("hidden_notice_title");
        this.hiddenNoticeContent = document.getElementById("hidden_notice_content");
        this.hiddenNoticeSummary = document.getElementById("hidden_notice_summary");
        this.noticeWriterEditor = this.getQuillEditor("notice_write_editor");
        this.viewSourceButton = document.getElementById("view-editor-source");
        this.isSubmitFlag = false;
    }

    initNoticeWriteController() {
        this.initEventListener();
        this.#initIntervalAutoSave();
    }

    #initIntervalAutoSave() {
        // localStorage 정보 반환 & 복구
        this.setAutoSaveWriteInfo(JSON.parse(localStorage.getItem("noticeSaveInfo")));
        // interval 실행
        this.noticeInterval = this.invokeAutoSaveInterval(() => {
            const jsonObj = {
                title: this.noticeTitle.value == null ? "" : this.noticeTitle.value,
                contents: this.noticeWriterEditor.root.innerHTML == null ? "" : this.noticeWriterEditor.root.innerHTML,
                uploadKey: document.getElementById("upload_key").value
            };
            localStorage.setItem("noticeSaveInfo", JSON.stringify(jsonObj));
        }, null, 1000 * 3);
    }

    initEventListener() {
        this.noticeWriteRegisterButton.addEventListener("click", evt => {
            if (this.isSubmitFlag === true) {
                this.showToastMessage("공지를 발행 중입니다.");
                return;
            }

            if (confirm('공지를 발행하겠습니까?')) {
                if (this.checkPostSubmitInfo()) {
                    this.showToastMessage("빈칸,공백만 포함 된 정보는 유효하지 않습니다.");
                } else {
                    const compressedContent = this.compressContent(this.noticeWriterEditor.root.innerHTML, true);

                    if (this.checkPostContentSize(compressedContent, this.MAX_POST_CONTENT_SIZE)) {
                        this.showToastMessage("공지 본문 크기가 허용 범위를 초과하였습니다.");
                        return;
                    }

                    this.isSubmitFlag = true;
                    this.hiddenNoticeTitile.value = this.noticeTitle.value;
                    this.hiddenNoticeContent.value = compressedContent;
                    this.hiddenNoticeSummary.value = this.replaceAndSubHTMlTag(this.noticeWriterEditor.root.innerHTML, 200);
                    this.clearInterval(this.noticeInterval, "noticeSaveInfo");
                    this.#uploadNotice();
                }
            }
        });

        this.viewSourceButton.addEventListener("click", evt => {
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
                               ${this.noticeWriterEditor.root.innerHTML}
                            </textarea>
                    </body>
                </html>`);
        });
    }

    setAutoSaveWriteInfo(autoSaveWriteInfo) {
        if (autoSaveWriteInfo != null) {
            this.noticeTitle.value = autoSaveWriteInfo["title"];
            this.noticeWriterEditor.root.innerHTML = autoSaveWriteInfo["contents"];
            document.getElementById("upload_key").value = autoSaveWriteInfo["uploadKey"];
        }
    }

    #uploadNotice() {
        const xhr = new XMLHttpRequest();
        const formData = new FormData(this.noticeWriteForm);

        xhr.open("POST", "/notice/write", true);
        xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));

        xhr.addEventListener("loadend", event => {
            const status = event.target.status;
            const responseValue = event.target.responseText;

            if ((status >= 400 && status <= 500) || (status > 500)) {
                this.showToastMessage(responseValue);
            } else {
                window.location.href = `/notice/write`;
            }
            this.isSubmitFlag = false;
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage('오류가 발생하여 공지사항 업로드에 실패하였습니다.');
        });
        xhr.send(formData);
    }

    checkPostSubmitInfo() {
        if (!this.noticeTitle.value ||
            ((this.noticeWriterEditor.root.innerText === null || this.getRemoveSpaceStr(this.noticeWriterEditor.root.innerHTML) === "<p></p>") ||
                (this.noticeWriterEditor.root.innerText.replace(/ /g, "") === null || this.getRemoveSpaceStr(this.noticeWriterEditor.root.innerHTML) === "<p></p>")) ||
            (!this.noticeWriterEditor.root.innerText.replace(/ /g, "") === null || this.getRemoveSpaceStr(this.noticeWriterEditor.root.innerHTML) === "<p></p>")) {
            return true;
        } else {
            return false;
        }
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const noticeWriteController = new NoticeWriteController();
    noticeWriteController.initNoticeWriteController();
});