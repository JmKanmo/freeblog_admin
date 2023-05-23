class SignUpController extends UtilController {
    constructor() {
        super();
        this.idCheckButton = document.getElementById("id-double_check_button");
        this.idInput = document.getElementById("user_id");
        this.emailCheckButton = document.getElementById("email-double_check_button");
        this.emailInput = document.getElementById("user_email");
        this.idCheckFlagInput = document.getElementById("id_check_flag");
        this.emailCheckFlagInput = document.getElementById("email_check_flag");
        this.userSignUpForm = document.getElementById("user_signup_form");
        this.isSubmitFlag = false;
    }

    initSignUpController() {
        this.idCheckButton.addEventListener("click", evt => {
            const id = this.idInput.value;

            if (this.checkIdRegExp(id) === false) {
                this.showToastMessage('id 패턴에 적합하지 않습니다.');
                return;
            }

            this.idCheckButton.disabled = true;

            const xhr = new XMLHttpRequest();
            xhr.open("GET", `/user/check-id?id=${id}`, true);

            xhr.addEventListener("loadend", event => {
                let status = event.target.status;
                const responseValue = event.target.responseText;

                if ((status >= 400 && status <= 500) || (status > 500)) {
                    this.showToastMessage(`${responseValue}`, false, 5000, () => {
                        this.idCheckButton.disabled = false;
                    });
                    this.idCheckFlagInput.value = false;
                } else {
                    this.showToastMessage(`${responseValue}`, false, 5000, () => {
                        this.idCheckButton.disabled = false;
                    });
                    this.idCheckFlagInput.value = true;
                }
            });

            xhr.addEventListener("error", event => {
                this.showToastMessage('id 중복확인에 실패하였습니다.', false, 5000, () => {
                    this.idCheckButton.disabled = false;
                });
            });
            xhr.send();
        });

        this.emailCheckButton.addEventListener("click", evt => {
            const email = this.emailInput.value;

            if (this.checkEmailRegExp(email) === false) {
                this.showToastMessage('이메일 패턴에 적합하지 않습니다.');
                return;
            }

            this.emailCheckButton.disabled = true;

            const xhr = new XMLHttpRequest();
            xhr.open("GET", `/user/check-email?email=${email}`, true);

            xhr.addEventListener("loadend", event => {
                let status = event.target.status;
                const responseValue = event.target.responseText;

                if ((status >= 400 && status <= 500) || (status > 500)) {
                    this.showToastMessage(`${responseValue}`, false, 5000, () => {
                        this.emailCheckButton.disabled = false;
                    });
                    this.emailCheckFlagInput.value = false;
                } else {
                    this.showToastMessage(`${responseValue}`, false, 5000, () => {
                        this.emailCheckButton.disabled = false;
                    });
                    this.emailCheckFlagInput.value = true;
                }
            });

            xhr.addEventListener("error", event => {
                this.showToastMessage('이메일 중복확인에 실패하였습니다.', false, 5000, () => {
                    this.emailCheckButton.disabled = false;
                });
            });
            xhr.send();
        });

        this.userSignUpForm.addEventListener("submit", evt => {
            if (this.isSubmitFlag === true) {
                this.showToastMessage("회원가입을 진행 중입니다.");
                return;
            }

            evt.preventDefault();

            if (confirm('회원가입 폼을 제출하시겠습니까?')) {
                this.userSignUpForm.submit();
                this.isSubmitFlag = true;
                return true;
            } else {
                return false;
            }
        });
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const signUpController = new SignUpController();
    signUpController.initSignUpController();
});