const User = ()=>{
    const TOKEN = localStorage.getItem("TOKEN");

    if (TOKEN == null) {
      window.location.replace("/login");
    }
    return (
        <>
        {localStorage.getItem("TOKEN")}

        </>
    )
}
export default User;