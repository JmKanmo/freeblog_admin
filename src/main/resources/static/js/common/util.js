/**
 * 각종 기능 유틸리티 컨트롤러
 * **/
class UtilController {
    constructor() {
        this.initHandlerbars();
        this.idRegex = new RegExp('^[a-z]{1}[a-z0-9]{4,11}$');
        this.emailRegex = new RegExp('^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$');
        this.defaultUserProfileThumbnail = "../images/user_default_thumbnail.png";

        // post
        this.MAX_POST_CONTENT_SIZE = 10 * 1024 * 1024 // 압축사이즈: 10MB

        // intro
        this.MAX_INTRO_CONTENT_SIZE = 5 * 1024 * 1024; // 압축사이즈: 5MB
    }

    initHandlerbars() {
        Handlebars.registerHelper('nullCheck', param => {
            return param == null || param == '<<<undefined>>>';
        });

        Handlebars.registerHelper('hrefCheck', param => {
            return !param ? 'comment' : param;
        });

        Handlebars.registerHelper('existImage', image => {
            return image != null && image != '<<<undefined>>>';
        });

        Handlebars.registerHelper('getInnerText', tag => {
            if (!tag) {
                return 'UNDEFINED';
            }
            return tag.replace(/(<([^>]+)>)/ig, "");
        });

        Handlebars.registerHelper('getUserProfileImage', image => {
            if (image === '<<<undefined>>>') {
                return '../images/comment_default_user_pic.png';
            } else {
                return image;
            }
        });

        Handlebars.registerHelper('getPostThumbnailImage', image => {
            if (image === '<<<undefined>>>') {
                return '../images/default_thumbnail.gif';
            } else {
                return image;
            }
        });

        Handlebars.registerHelper('getDefaultPostImage', image => {
            if (image === '<<<undefined>>>') {
                return '../images/default_post_image.png';
            } else {
                return image;
            }
        });

        Handlebars.registerHelper('getCheckedUserName', name => {
            if (!name || name === '<<<undefined>>>') {
                return '익명의유저';
            } else {
                return name;
            }
        });
    }

    checkPostContentSize(content, size) {
        return content > size;
    }

    getRemoveSpaceStr(str) {
        return str.replace(/\s/g, '');
    }

    getUrlStr() {
        return window.location.href;
    }

    getUrlStrAndParse(separator) {
        const url = this.getUrlStr();
        const parsed = url.split(separator);

        if (parsed.length > 1) {
            return parsed[1];
        } else {
            return null;
        }
    }

    scrollTargetElement(id) {
        if (!id) {
            return;
        }
        document.getElementById(id).scrollIntoView();
    }

    /**
     * 오픈소스 참조 (로딩 중 화면 만들기)
     * 추후 다른 라이브러리 대체
     * **/
    loadingWithMask(width, height) {
        //화면의 높이와 너비를 구합니다.
        const maskHeight = height;
        const maskWidth = width;

        //화면에 출력할 마스크를 설정해줍니다.
        const mask = `<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>`;
        let loadingImg = ``;

        loadingImg += "<div id='loadingImg' style='position:absolute; top: calc(50% - (200px / 2)); width:100%; z-index:99999999;'>";
        loadingImg += `<img src='../images/loading_img.gif' style='position: relative; display: block; margin: 0px auto;'/>`;
        loadingImg += `</div>`;

        //화면에 레이어 추가
        $('body')
            .append(mask)
            .append(loadingImg)

        //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
        $('#mask').css({
            'width': maskWidth
            , 'height': maskHeight
            , 'opacity': '0.3'
        });

        //마스크 표시
        $('#mask').show();

        //로딩중 이미지 표시
        $('#loadingImg').show();
    }

    /** 오픈소스 참조 (로딩 중 화면 닫기) **/
    closeLoadingWithMask() {
        $('#mask, #loadingImg').hide();
        $('#mask, #loadingImg').empty();
    }

    sleep(ms) {
        return new Promise((resolve) => setTimeout(resolve, ms))
    }

    showToastMessage(message) {
        Toastify({
            text: message,
            duration: 3000,
            close: true,
            position: "center",
            stopOnFocus: true,
            style: {
                background: "linear-gradient(to right, #00b09b, #96c93d)",
            }
        }).showToast();
    }

    showToastMessage(message, isClose, duration, dismissListener) {
        Toastify({
            text: message,
            duration: duration,
            close: isClose,
            position: "center",
            stopOnFocus: true,
            style: {
                background: "linear-gradient(to right, #00b09b, #96c93d)",
            },
            callback: dismissListener
        }).showToast();
    }

    checkSpecialCharacter(text) {
        const regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/g;

        if (regExp.test(text)) {
            return true;
        } else {
            return false;
        }
    }

    checkEmailRegExp(email) {
        return this.emailRegex.test(email);
    }

    checkIdRegExp(id) {
        return this.idRegex.test(id);
    }

    openPopUp(width, height, url, target) {
        let left = (document.body.offsetWidth / 2) - (width / 2);
        let tops = (document.body.offsetHeight / 2) - (height / 2);
        left += window.screenLeft;
        return window.open(url, target, `width=${width}, height=${height}, left=${left}, top=${tops}`);
    }

    convertStrByteArr(obj, isEncode) {
        if (isEncode === true) {
            return obj.toString()
        } else {
            const parsed = obj.split(",");
            const uint8Arr = new Uint8Array(parsed.length);
            for (let i = 0; i < parsed.length; i++) {
                let elem = parseInt(parsed[i]);
                elem = (elem < 0) ? (elem * -1) : elem;
                uint8Arr[i] = elem;
            }
            return uint8Arr;
        }
    }

    /**
     * Compress & Decompress string content
     */
    compressContent(content, isCompress) {
        if (content == null) {
            return "";
        }

        if (isCompress === true) {
            const compressed = LZString.compressToBase64(content);
            return !compressed ? content : compressed;
        } else {
            const decompressed = LZString.decompressFromBase64(content);
            return (!decompressed || decompressed === 'P') ? content : decompressed;
        }
    }

    /**
     * HTML Content remove replace method
     */
    replaceAndSubHTMlTag(tag, limit) {
        const result = (tag == null) ? "" : tag.replace(/(<([^>]+)>)/ig, '');
        return limit <= 0 ? result : result.substring(0, limit);
    }

    /**
     * Quill Editor Utils
     * **/
    getQuillHTML(htmlTag, data_gramm, contentEditable) {
        return (
            `<div class="ql-editor" data-gramm="${data_gramm}" contenteditable="${contentEditable}" data-placeholder="원하는 문장을 자유롭게 입력하세요. :)">` +
            htmlTag +
            `</div>`
        );
    }

    getReadOnlyQuillEditor(id) {
        Quill.register("modules/imageCompressor", imageCompressor);

        return new Quill(`#${id}`, {
            modules: {
                syntax: true,
                toolbar: false
            },
            theme: 'snow',
            readOnly: true
        });
    }

    getQuillEditor(id) {
        Quill.register("modules/imageCompressor", imageCompressor);
        const quill = new Quill(`#${id}`, {
            modules: {
                "emoji-toolbar": true,
                "emoji-shortname": true,
                "emoji-textarea": true,
                imageDrop: true,
                imageResize: {
                    modules: ['Resize', 'DisplaySize', 'Toolbar']
                },
                syntax: true,
                // imageCompressor: {
                //     quality: 1,
                //     maxWidth: 1000, // default
                //     maxHeight: 1000, // default
                //     ignoreImageTypes: ['image/gif']
                // },
                toolbar: [
                    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
                    ['blockquote', 'code-block'],
                    [{'header': 1}, {'header': 2}],               // custom button values
                    [{'list': 'ordered'}, {'list': 'bullet'}],
                    [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
                    [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
                    [{'direction': 'rtl'}],                         // text direction
                    ['link', 'image', 'video', 'formula'],          // add image support
                    [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
                    [{'header': [1, 2, 3, 4, 5, 6, false]}],
                    [{'color': []}, {'background': []}],          // dropdown with defaults from theme
                    [{'font': []}],
                    [{'align': []}],
                    ['clean'],                                        // remove formatting button
                    ['emoji']
                ]
            },
            'image-tooltip': true,
            'link-tooltip': true,
            theme: 'snow',
            placeholder: '원하는 문장을 자유롭게 입력하세요. :)'
        });

        quill.getModule('toolbar').addHandler('image', () => {
            const input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*');
            input.click();

            input.addEventListener("change", async () => {
                const imgFile = input.files[0];
                try {
                    if (this.checkImageFileExtension(imgFile, ['jpg', 'jpeg', 'png', 'gif', 'GIF'])) {
                        if (this.checkImageFileExtension(imgFile, ['gif', 'GIF']) && this.checkImageFileBySize(imgFile, 5 * 1024 * 1024)) {
                            // if file extension is gif | GIF, 5MB가 넘지 않는 경우, 압축 진행 X
                            const fileReader = new FileReader();

                            fileReader.onload = (event) => {
                                const formData = new FormData();
                                const xhr = new XMLHttpRequest();
                                const uploadKeyDocument = document.getElementById("upload_key")
                                let uploadKey = uploadKeyDocument.value;

                                if (!uploadKey || uploadKey === '' || uploadKey === 'undefined') {
                                    uploadKey = new Date().getTime();
                                    uploadKeyDocument.value = uploadKey;
                                }

                                xhr.open("POST", `/post/upload/post-image/${uploadKey}`, true);
                                xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));

                                xhr.addEventListener("loadend", event => {
                                    let status = event.target.status;
                                    const responseValue = event.target.responseText;

                                    if ((status >= 400 && status <= 500) || (status > 500)) {
                                        this.showToastMessage(responseValue);
                                    } else {
                                        quill.editor.insertEmbed(quill.getSelection().index, 'image', responseValue);
                                    }
                                });

                                xhr.addEventListener("error", event => {
                                    this.showToastMessage('오류가 발생하여 이미지 전송에 실패하였습니다.');
                                });

                                formData.append("compressed_post_image", imgFile);
                                xhr.send(formData);
                            }
                            fileReader.readAsDataURL(imgFile);
                        } else {
                            this.getCompressedImageFile(imgFile).then(compressedImgFile => {
                                const fileReader = new FileReader();

                                fileReader.onload = (event) => {
                                    const formData = new FormData();
                                    const xhr = new XMLHttpRequest();
                                    const uploadKeyDocument = document.getElementById("upload_key")
                                    let uploadKey = uploadKeyDocument.value;

                                    if (!uploadKey || uploadKey === '' || uploadKey === 'undefined') {
                                        uploadKey = new Date().getTime();
                                        uploadKeyDocument.value = uploadKey;
                                    }

                                    xhr.open("POST", `/post/upload/post-image/${uploadKey}`, true);
                                    xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));

                                    xhr.addEventListener("loadend", event => {
                                        let status = event.target.status;
                                        const responseValue = event.target.responseText;

                                        if ((status >= 400 && status <= 500) || (status > 500)) {
                                            this.showToastMessage(responseValue);
                                        } else {
                                            quill.editor.insertEmbed(quill.getSelection().index, 'image', responseValue);
                                        }
                                    });

                                    xhr.addEventListener("error", event => {
                                        this.showToastMessage('오류가 발생하여 이미지 전송에 실패하였습니다.');
                                    });

                                    formData.append("compressed_post_image", compressedImgFile);
                                    xhr.send(formData);
                                }
                                fileReader.readAsDataURL(compressedImgFile);
                            });
                        }
                    } else {
                        this.showToastMessage("지정 된 이미지 파일 ('jpg', 'jpeg', 'png', 'gif', 'GIF')만 업로드 가능합니다.");
                    }
                } catch (error) {
                    this.showToastMessage("ERROR: " + error);
                }
            })
        });

        /**
         * Batch Job 통해 주기(N년) 동안 참조되지 않은 리소스 삭제하도록 (방법 조사) ...
         * 주석 처리
         */
        // quill.on('text-change', (delta, oldContents, source) => {
        //     if (source !== 'user') return;
        //     const deletedImgList = this.getQuillEditorImgUrls(quill.getContents().diff(oldContents));
        //
        //     if (deletedImgList && deletedImgList.length > 0) {
        //         const formData = new FormData();
        //         const xhr = new XMLHttpRequest();
        //
        //         xhr.open("POST", `/post/delete/post-image`, true);
        //         xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf']").attr("content"));
        //
        //         xhr.addEventListener("loadend", event => {
        //             let status = event.target.status;
        //             const responseValue = event.target.responseText;
        //
        //             if ((status >= 400 && status <= 500) || (status > 500)) {
        //                 this.showToastMessage(responseValue);
        //             }
        //         });
        //
        //         xhr.addEventListener("error", event => {
        //             this.showToastMessage('오류가 발생하여 이미지 삭제에 실패하였습니다.');
        //         });
        //
        //         formData.append("imgSrcList", deletedImgList);
        //         xhr.send(formData);
        //     }
        // });

        return quill;
    }

    getQuillEditorImgUrls(delta) {
        return delta.ops.filter(i => i.insert && i.insert.image).map(i => i.insert.image);
    }

    initAudioPlayer() {
        const ap = new APlayer({
            container: document.getElementById('audio_player'),
            listFolded: true,
            listMaxHeight: 90,
            lrcType: 0,
            audio: [
                {
                    name: '너에게 쓰는 편지',
                    artist: 'MC몽(Feat.린)',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/02.MC%EB%AA%BD+-+%EB%84%88%EC%97%90%EA%B2%8C+%EC%93%B0%EB%8A%94+%ED%8E%B8%EC%A7%80+(Feat.%EB%A6%B0).mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/artworks-AJN3y0tBC4l8aVPf-3lk1MQ-original.jpg',
                    theme: '알앤비'
                },
                {
                    name: '그리워하다',
                    artist: '비투비',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/BTOB(%EB%B9%84%ED%88%AC%EB%B9%84)+-+%EA%B7%B8%EB%A6%AC%EC%9B%8C%ED%95%98%EB%8B%A4.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/artworks-000358972065-nx6b7a-original.jpg',
                    theme: '알앤비'
                },
                {
                    name: '인기',
                    artist: 'MC몽',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/MC%EB%AA%BD+-+%EC%9D%B8%EA%B8%B0.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/ssongya.png',
                    theme: '알앤비'
                },
                {
                    name: 'Monologue',
                    artist: '테이',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%ED%85%8C%EC%9D%B4+-+Monologue.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%ED%85%8C%EC%9D%B4_%EB%AA%A8%EB%85%B8%EB%A1%9C%EA%B7%B8.jpg',
                    theme: '발라드'
                },
                {
                    name: '꼭돌아오리',
                    artist: '임선희',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%EA%BC%AD+%EB%8F%8C%EC%95%84%EC%98%A4%EB%A6%AC.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EB%8B%AC%EC%9D%98%EC%97%B0%EC%9D%B8.jpg',
                    theme: '알앤비'
                },
                {
                    name: '가을밤 떠난 너',
                    artist: '케이시',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%EA%B7%B8%EB%95%8C%EA%B0%80+%EC%A2%8B%EC%95%98%EC%96%B4.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EC%BC%80%EC%9D%B4%EC%8B%9C%EC%82%AC%EC%A7%84.jpg',
                    theme: '알앤비'
                },
                {
                    name: '언제나 사랑해',
                    artist: '케이시',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%EC%96%B8%EC%A0%9C%EB%82%98+%EC%82%AC%EB%9E%91%ED%95%B4.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EC%BC%80%EC%9D%B4%EC%8B%9C%EC%82%AC%EC%A7%84.jpg',
                    theme: '알앤비'
                },
                {
                    name: '사랑과 우정 사이',
                    artist: 'SG워너비',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/SG%EC%9B%8C%EB%84%88%EB%B9%84+-+%EC%82%AC%EB%9E%91%EA%B3%BC+%EC%9A%B0%EC%A0%95%EC%82%AC%EC%9D%B4.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EC%98%88%EC%81%9C%ED%95%98%EB%8A%98.jpg',
                    theme: '알앤비'
                },
                {
                    name: '가을 안부',
                    artist: '먼데이키즈',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%EA%B0%80%EC%9D%84+%EC%95%88%EB%B6%80.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EA%B0%80%EC%9D%84%EC%95%88%EB%B6%80.jpg',
                    theme: '발라드'
                },
                {
                    name: '겨울..그다음 봄',
                    artist: '로시(Rothy)',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%EB%A1%9C%EC%8B%9C(Rothy)+-+%EA%B2%A8%EC%9A%B8..+%EA%B7%B8%EB%8B%A4%EC%9D%8C+%EB%B4%84.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EB%B4%84%EA%B7%B8%EB%A6%AC%EA%B3%A0%EA%B2%A8%EC%9A%B8.jpg',
                    theme: '발라드'
                },
                {
                    name: 'Nostalgia',
                    artist: '요조',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%EC%9A%94%EC%A1%B0++Nostalgia.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EA%B0%80%EC%9D%84%EC%95%88%EB%B6%80.jpg',
                    theme: '알앤비'
                },
                {
                    name: '우주를 줄게',
                    artist: '볼빨간사춘기',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/Bolbbalgan4+(%EB%B3%BC%EB%B9%A8%EA%B0%84%EC%82%AC%EC%B6%98%EA%B8%B0)-+Galaxy+(%EC%9A%B0%EC%A3%BC%EB%A5%BC+%EC%A4%84%EA%B2%8C)+(3D+Audio%2C+Use+Headphones!).mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EB%B3%BC%EB%B9%A8%EA%B0%84%EC%82%AC%EC%B6%98%EA%B8%B0.jpg',
                    theme: '알앤비'
                },
                {
                    name: 'rainyday',
                    artist: '파테코(PATEKO)',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/PATEKO+(%ED%8C%8C%ED%85%8C%EC%BD%94)+-+Rainy+day+(Feat.+ASH+ISLAND%2C+Skinny+Brown).mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/rainyday.jpg',
                    theme: '힙합'
                },
                {
                    name: 'hype-boy',
                    artist: '뉴진스',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/hype-boy.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EB%89%B4%EC%A7%84%EC%8A%A4.jpg',
                    theme: 'rnb'
                },
                {
                    name: 'its you',
                    artist: '샘킴',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/it\'s-you.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/2022-10-31T02%3A55%3A39.7135106008c2b18d0-13c0-37fe-b303-1c23f116e4b9.png',
                    theme: 'rnb'
                },
                {
                    name: '솜사탕',
                    artist: '풍댕이',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%ED%92%8D%EB%8C%95%EC%9D%B4+-+cotton+candy.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/2022-08-23T08%3A59%3A46.89279060054c6e247-cac0-37da-af49-8a1950e4f618.jpg',
                    theme: '청순섹시'
                },
                {
                    name: 'strawberry moon',
                    artist: 'IU',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%EC%95%84%EC%9D%B4%EC%9C%A0+-+strawberry+moon.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/iu_strawberrymoon.jpg',
                    theme: '달콤'
                },
                {
                    name: 'celebrity',
                    artist: 'IU',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/Celebrity+-+%EC%95%84%EC%9D%B4%EC%9C%A0.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/iu_celebrity.jpg',
                    theme: '달콤'
                },
                {
                    name: '잠못드는밤 비는 내리고',
                    artist: 'IU',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%EC%95%84%EC%9D%B4%EC%9C%A0+-+%EC%9E%A0+%EB%AA%BB+%EB%93%9C%EB%8A%94+%EB%B0%A4+%EB%B9%84%EB%8A%94+%EB%82%B4%EB%A6%AC%EA%B3%A0.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EC%95%84%EC%9D%B4%EC%9C%A0%EA%B0%95%EC%95%84%EC%A7%80.jpg',
                    theme: 'R&B'
                },
                {
                    name: '불가살 - 하루',
                    artist: '포맨',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/4MEN+(%ED%8F%AC%EB%A7%A8)+-+%ED%95%98%EB%A3%A8+(Bulgasal_+Immortal+Souls+%EB%B6%88%EA%B0%80%EC%82%B4+OST+Part+1).mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EB%B6%88%EA%B0%80%EC%82%B4_%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg',
                    theme: '불가살 테마'
                },
                {
                    name: '너,너',
                    artist: '스트레이',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/%EC%8A%A4%ED%8A%B8%EB%A0%88%EC%9D%B4++%EB%84%88+%EB%84%88.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/%EC%8A%A4%ED%8A%B8%EB%A0%88%EC%9D%B4+%EB%84%88+%EB%84%88.jpg',
                    theme: '발라드 R&B'
                }
                ,
                {
                    name: 'dynamite',
                    artist: 'BTS',
                    url: 'https://freelog-s3-bucket.s3.amazonaws.com/audio/BTS_-_Dynamite.mp3',
                    cover: 'https://freelog-s3-bucket.s3.amazonaws.com/image/2022-08-23T09%3A02%3A46.012406500cfe847cc-b1cd-3a80-afd9-b2e5e6e183c0.png',
                    theme: '댄스, 빌보드'
                }
            ]
        });
        return ap;
    }

    initCalendar() {
        const calendarEl = document.getElementById('calendar');
        const calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth', // 초기 로드 될때 보이는 캘린더 화면(기본 설정: 달)
            titleFormat: function (date) {
                return date.date.year + '년 ' + (parseInt(date.date.month) + 1) + '월';
            },
            //initialDate: '2021-07-15', // 초기 날짜 설정 (설정하지 않으면 오늘 날짜가 보인다.)
            selectable: true, // 달력 일자 드래그 설정가능
            droppable: true,
            editable: true,
            nowIndicator: true, // 현재 시간 마크
            locale: 'ko' // 한국어 설정
        });
        calendar.render();
    }

    getDefaultUserProfileThumbnail() {
        return this.defaultUserProfileThumbnail;
    }

    async compressImageFiles(imgFile) {
        return await imageCompression(
            imgFile,
            // you should provide one of maxSizeMB, maxWidthOrHeight in the options
            {
                maxSizeMB: 5 * 1024 * 1024,          // 5MB
                onProgress: Function,       // optional, a function takes one progress argument (percentage from 0 to 100)
                useWebWorker: true,      // optional, use multi-thread web worker, fallback to run in main-thread (default: true)
            });
    }

    async getCompressedImageFile(imgFile) {
        return await this.compressImageFiles(imgFile).then(compressedImgFile => {
            return compressedImgFile;
        });
    }

    checkImageFile(imgFile) {
        if (imgFile) {
            if (!this.checkImageFileExtension(imgFile, ['jpg', 'jpeg', 'png', 'gif', 'GIF'])) {
                this.showToastMessage("지정 된 이미지 파일 ('jpg', 'jpeg', 'png', 'gif', 'GIF')만 업로드 가능합니다.");
                return false;
            } else if (!this.checkImageFileBySize(imgFile, 5 * 1024 * 1024)) {
                this.showToastMessage('최대 업로드 파일 크기는 5MB 입니다.');
                return false;
            }
        }
        return true;
    }

    checkImageFile(imgFile, size) {
        if (imgFile) {
            if (!this.checkImageFileExtension(imgFile, ['jpg', 'jpeg', 'png', 'gif', 'GIF'])) {
                this.showToastMessage("지정 된 이미지 파일 ('jpg', 'jpeg', 'png', 'gif', 'GIF')만 업로드 가능합니다.");
                return false;
            } else if (!this.checkImageFileBySize(imgFile, size * 1024 * 1024)) {
                this.showToastMessage('최대 업로드 파일 크기는 ' + size + 'MB 입니다.');
                return false;
            }
        }
        return true;
    }

    checkImageFileExtension(imgFile, fileForms) {
        if (imgFile) {
            const fileExtension = imgFile.name.slice(imgFile.name.lastIndexOf(".") + 1);

            if (!fileForms.includes(fileExtension)) {
                return false;
            } else {
                return true;
            }
        }
    }

    checkImageFileBySize(imgFile, fileSize) {
        if (imgFile) {
            if (imgFile.size >= fileSize) {
                return false;
            }
        }
        return true;
    }

    getQueryParam(page, recordSize, pageSize) {
        return new URLSearchParams({
            page: (page) ? page : 1,
            recordSize: recordSize,
            pageSize: pageSize
            // TODO 필요에 따라 keyword, searchType 등등 추가로 정의
        });
    }

    drawBasicPagination(pagination, queryParam, url) {
        let html = '';

        // 첫 페이지, 이전 페이지
        if (pagination["existPrevPage"]) {
            html += `
              <!--
               <li class="page-item">
                    <button class="page-link" aria-label="Previous" url="${url}" page="1">
                    <span aria-hidden="true">&laquo;</span>
                    </button>
                </li>
              -->
                
                <li class="page-item">
                    <button class="page-link" aria-label="Previous" url="${url}" page="${pagination["startPage"] - 1}">
                    <span aria-hidden="true">&laquo;</span>
                    </button>
                </li>
            `;
        }

        // 페이지 번호
        for (let i = pagination["startPage"]; i <= pagination["endPage"]; i++) {
            if (i <= 0)
                continue;

            const active = (i === parseInt(queryParam.get("page"))) ? 'active' : '';
            html += `
             <li class="page-item ${active}">
                    <button class="page-link" url="${url}" page="${i}">${i}</button>
                </li>
            `;
        }

        // 다음 페이지, 마지막 페이지
        if (pagination["existNextPage"]) {
            html += `
             <li class="page-item">
                    <button class="page-link" aria-label="Previous" url="${url}" page="${pagination["endPage"] + 1}">
                    <span aria-hidden="true">&raquo;</span>
                    </button>
                </li>
                
                <!--
                <li class="page-item">
                    <button class="page-link" aria-label="Previous" url="${url}" page="${pagination["totalPageCount"]}">
                    <span aria-hidden="true">&lsaquo;</span>
                    </button>
                </li>
                -->
            `;
        }
        return html;
    }

    drawSimplePagination(pagination, queryParam, url) {
        let html = '';

        // 첫 페이지, 이전 페이지
        if (pagination["existPrevPage"]) {
            html += `
               <!--
               <li class="page-item">
                    <button class="page-link simple_page_button_style" aria-label="Previous" url="${url}" page="1">
                    <span aria-hidden="true">&laquo;</span>
                    </button>
                </li>
                -->
                
                <li class="page-item">
                    <button class="page-link simple_page_button_style" aria-label="Previous" url="${url}" page="${pagination["startPage"] - 1}">
                    <span aria-hidden="true">&laquo;</span>
                    </button>
                </li>
            `;
        }

        // 페이지 번호
        for (let i = pagination["startPage"]; i <= pagination["endPage"]; i++) {
            if (i <= 0)
                continue;

            const active = (i === parseInt(queryParam.get("page"))) ? 'active' : '';
            html += `
             <li class="page-item ${active}">
                    <button class="page-link simple_page_button_style" url="${url}" page="${i}">${i}</button>
                </li>
            `;
        }

        // 다음 페이지, 마지막 페이지
        if (pagination["existNextPage"]) {
            html += `
             <li class="page-item">
                    <button class="page-link simple_page_button_style" aria-label="Previous" url="${url}" page="${pagination["endPage"] + 1}">
                    <span aria-hidden="true">&raquo;</span>
                    </button>
                </li>
                
                <!--
                <li class="page-item">
                    <button class="page-link simple_page_button_style" aria-label="Previous" url="${url}" page="${pagination["totalPageCount"]}">
                    <span aria-hidden="true">&lsaquo;</span>
                    </button>
                </li>
                -->
            `;
        }
        return html;
    }

    copyUrl() {
        const url = window.document.location.href;
        const textarea = document.createElement("textarea");
        document.body.appendChild(textarea);
        textarea.value = url;
        textarea.select();
        document.execCommand("copy");
        document.body.removeChild(textarea);
        alert("URL이 복사되었습니다.")
    }

    invokeAutoSaveInterval(func, contents, time) {
        return setInterval(func, time);
    }

    clearInterval(interval, intervalKey) {
        clearInterval(interval);
        localStorage.removeItem(intervalKey);
    }
}

/**
 * 로그인 팝업 컨트롤러
 */
class LoginPopUpController extends UtilController {
    constructor() {
        super();

        // pop up
        this.formPopUpWindow = document.getElementById("formPopUp");
        this.loginButton = document.getElementById("login_button");
        this.popUpCloseButton = document.getElementById("closePopUpButton");
    }

    initLoginPopUpController() {
        // login button event listener
        if (this.loginButton != null) {
            this.loginButton.addEventListener("click", evt => {
                if (this.formPopUpWindow.style.display === '' || this.formPopUpWindow.style.display === 'none') {
                    this.formPopUpWindow.style.display = 'block';
                }
            });
        }

        // login pop up event listener
        if (this.popUpCloseButton != null) {
            this.popUpCloseButton.addEventListener("click", evt => {
                this.formPopUpWindow.style.display = 'none';
            });
        }
    }
}