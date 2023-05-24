// Execute all functions

class SignUpCompleteController extends UtilController {
    constructor() {
        super();
    }

    initSignUpCompleteController() {
        this.resendButtonInit();
    }

    resendButtonInit() {
        // TODO
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const signUpController = new SignUpCompleteController();
    signUpController.initSignUpCompleteController();
});