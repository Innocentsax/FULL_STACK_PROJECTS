const Logout=()=>{
    localStorage.removeItem("TOKEN");
    window.location.replace("/login")
    return(
        <>
        </>
    )
}

export default Logout;