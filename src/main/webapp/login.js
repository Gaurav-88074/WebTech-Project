window.onload = function (params) {
    document.querySelector(".logo-name").addEventListener("click", (e) => {
        window.location.href = "./dashboard.jsp";
    });
    document.getElementById("signup-button").addEventListener("click", (e) => {
        window.location.href = "./signup.jsp";
    });
    let ACCESS_TOKEN = "accessToken"
    let REFRESH_TOKEN = "refreshToken"
    document.getElementById("loginButton").addEventListener("click", async () => {
        const email = document.getElementById("emailInput").value;
        const password = document.getElementById("passwordInput").value;
        // console.log(email);
        // console.log(password);
        if (email.length == 0 || password.length == 0) {
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
                email: email,
                password: password,
            }),
        };

        // fetch("http://localhost:5000/api/teacher", options)
        const response = await fetch("http://localhost:8080/auth/login", options);
        if(response.status===200){
            const data= await  response.json();
            localStorage.setItem(ACCESS_TOKEN,data.accessToken);
            localStorage.setItem(REFRESH_TOKEN,data.refreshToken);
            // console.table(data);
            window.location.replace("./dashboard.jsp");
        }
        else{
            // console.log("invalid username and password")
            alert("Invalid Credential!!");
        }

    });
};