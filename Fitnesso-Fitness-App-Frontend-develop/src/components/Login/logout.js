// const homeurl = "http://localhost:3000/";
const homeurl = "https://fitnessoapp1.web.app/";


const logOut = (e) => {
    // e.preventDefault();
    if (confirm("Log out?")) {
        localStorage.removeItem("token")
        localStorage.removeItem("address")
        localStorage.removeItem("personInfo")
      localStorage.clear()
      window.location.replace(homeurl)
    }
}

export default logOut;