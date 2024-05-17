window.onload = function (params) {
    console.log("Yeah")
    document.querySelector(".logo-name").addEventListener("click", (e) => {
        window.location.href = "./dashboard.jsp";
    })
    document.getElementById("login-button").addEventListener("click", (e) => {
        window.location.href = "./index.jsp";
    })
    document.getElementById("signupButton").addEventListener("click", () => {
        const email = document.getElementById("emailInput").value;
        const username = document.getElementById("nameInput").value;
        const password = document.getElementById("passwordInput").value;
        console.log("pressed")
        if (email.length === 0 || username.length === 0 || password.length === 0) {
            alert("input fields cannot be empty ");
            return;
        }
        // console.log(email);
        // console.log(username);
        // console.log(password);
        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "name": username,
                "email": email,
                "password": password
            }),
        };
        console.log(options);
        fetch("http://localhost:8080/auth/signup", options)
            .then((response) => response.json())
            .then((response) => {
                // console.log(response);
                window.location.href = "./index.jsp";
            })
            .catch((err) => console.error(err));

    })
}