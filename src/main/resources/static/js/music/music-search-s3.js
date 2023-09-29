class MusicSearchController extends UtilController {
    constructor() {
        super();
        this.searchKeyword = document.getElementById("searchKeyword");
        this.musicS3SearchButton = document.getElementById("musicS3SearchButton");
        this.musicS3TotalSearchButton = document.getElementById("musicS3TotalSearchButton");
        this.uploadResultBox = document.getElementById("uploadResultBox");
        this.jsonUtilController = new JsonUtilController();
    }

    initMusicSearchController() {
        this.initEventListener();
    }

    initEventListener() {
        this.musicS3SearchButton.addEventListener("click", evt => {
            if (confirm("해당 키워드로 조회 하겠습니까?")) {
                const xhr = new XMLHttpRequest();
                const searchKeyword = !this.searchKeyword.value ? 'UNDEFINED' : this.searchKeyword.value;
                xhr.open("GET", `/music/search/s3/${searchKeyword}`, true);

                xhr.addEventListener("loadend", event => {
                    let status = event.target.status;

                    if ((status >= 400 && status <= 500) || (status > 500)) {
                        this.showToastMessage(event.target.responseText);
                    } else {
                        while (this.uploadResultBox.firstChild) {
                            this.uploadResultBox.removeChild(this.uploadResultBox.firstChild);
                        }
                        this.uploadResultBox.appendChild(this.jsonUtilController.printJsonStrSyntaxHighlight(event.target.responseText));
                    }
                });

                xhr.addEventListener("error", event => {
                    this.showToastMessage("뮤직 정보를 S3에서 조회 하는데 실패 하였습니다.");
                });

                xhr.send();
            }
        });

        this.musicS3TotalSearchButton.addEventListener("click", evt => {
            if (confirm("전체 조회 하겠습니까?")) {
                const xhr = new XMLHttpRequest();
                xhr.open("GET", `/music/total-search/s3`, true);

                xhr.addEventListener("loadend", event => {
                    let status = event.target.status;

                    if ((status >= 400 && status <= 500) || (status > 500)) {
                        this.showToastMessage(event.target.responseText);
                    } else {
                        while (this.uploadResultBox.firstChild) {
                            this.uploadResultBox.removeChild(this.uploadResultBox.firstChild);
                        }
                        this.uploadResultBox.appendChild(this.jsonUtilController.printJsonStrSyntaxHighlight(event.target.responseText));
                    }
                });

                xhr.addEventListener("error", event => {
                    this.showToastMessage("뮤직 정보를 S3에서 조회 하는데 실패 하였습니다.");
                });

                xhr.send();
            }
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicSearchController = new MusicSearchController();
    musicSearchController.initMusicSearchController();
});
