window.onload = function (elementId) {
    //-------------------------------------------------------
    let ACCESS_TOKEN = "accessToken"
    let REFRESH_TOKEN = "refreshToken"
    document.getElementById("logout-button").addEventListener("click",() => {
        localStorage.removeItem(ACCESS_TOKEN);
        localStorage.removeItem(REFRESH_TOKEN);
        window.location.replace("./index.jsp");
    })
    const token = localStorage.getItem(ACCESS_TOKEN);
    const [header, payload, signature] = token.split('.');
    const decodedPayload = JSON.parse(atob(payload));
    // console.log(decodedPayload)
    document.getElementById("user-info").innerText = `Welcome ${decodedPayload.username}`
};