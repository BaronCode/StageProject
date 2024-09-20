const container = document.querySelector('.container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

registerBtn.addEventListener('click', () => {
    container.classList.add('active');
});

loginBtn.addEventListener('click', () =>{
    container.classList.remove('active');
});

const icons = document.querySelectorAll(".show-hide");
icons.forEach((lockicon) => {
    lockicon.addEventListener("click", () => {
        const pinput = lockicon.parentElement.querySelector("input");
        if(pinput.type === "password"){
            lockicon.classList.replace("fa-lock","fa-unlock")
            return pinput.type = "text";
        }
        lockicon.classList.replace("fa-unlock","fa-lock");
        pinput.type = "password";
    })
})