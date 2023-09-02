class MusicController extends UtilController {
    constructor() {
        super();
        this.musicUtilController = new MusicUtilController();
        this.musicAddButton = document.getElementById("musicAddButton");
        this.musicUpdateButton = document.getElementById("musicUpdateButton");
        this.musicCategoryAddButton = document.getElementById("musicCategoryAddButton");
        this.musicCategoryUpdateButton = document.getElementById("musicCategoryUpdateButton");
        this.musicCategoryDeleteButton = document.getElementById("musicCategoryDeleteButton");
    }

    initMusicController() {
        this.initEventListener();
        this.musicUtilController.initAudioPlayer(
            {
                data: {
                    all: {
                        name: 'all',
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
                    }
                },
                config: {
                    listFolded: true,
                    listMaxHeight: 90,
                    lrcType: 0,
                    autoplay: false,
                    mutex: true,
                    order: 'random',
                    mode: {
                        fixed: true,
                        mini: false
                    }
                }
            }
        );
    }

    #requestMusicInfo() {
        const xhr = new XMLHttpRequest();
        const blogId = document.getElementById("blog_info_id").value;
        xhr.open("GET", `/post/recent/${blogId}`, true);

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;
            const responseValue = JSON.parse(event.target.responseText);

            if (((status >= 400 && status <= 500) || (status > 500)) || (status > 500)) {
                this.showToastMessage(responseValue["message"]);
            } else {
                // TODO
            }
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage("음악 정보를 불러오는데 실패하였습니다.");
        });
        xhr.send();
    }

    initEventListener() {
        this.musicAddButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music/add', 'popup');
        });

        this.musicUpdateButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music/update', 'popup');
        });

        this.musicCategoryAddButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music-category/add', 'popup');
        });

        this.musicCategoryUpdateButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music-category/update', 'popup');
        });

        this.musicCategoryDeleteButton.addEventListener("click", evt => {
            this.openPopUp(988, 750, '/music-category/delete', 'popup');
        });
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const musicController = new MusicController();
    musicController.initMusicController();
});