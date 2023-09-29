import jwt_decode from "jwt-decode";
 

export const decodeJwt = (token) => {
    const decoded = jwt_decode(token);
    
    /* prints:
    * { iss: "self",
    *   exp: 1393286893,
    *   iat: 1393268893  
    *   roles: "USER"
    *   sub: email
    * }
    */
    // decode header by passing in options (useful for when you need `kid` to verify a JWT):
    const decodedHeader = jwt_decode(token, { header: true });
    console.log(decodedHeader)

    return decoded

/* prints:
 * { typ: "JWT",
 *   alg: "HS256" }
 */
}

export const isTokenValid = (token) => {
    if(token === '') return false
    const decoded = jwt_decode(token);

    if(Date.now() >= decoded.exp * 1000) {
        console.log("Token expired!")
        return false
    }
    return true

}

export const redirectToUserPage = (location, navigate, roles) => {
    let from = location.state?.from?.pathname
    
    if(isTokenValid(localStorage.getItem("signature"))){
        if(roles === "ADMIN" || roles === "SUPERADMIN")
            from = location.state?.from?.pathname || "/admin"
        else if(roles === "CUSTOMER")
            from = location.state?.from?.pathname || "/shop"
    }else {
        from = location.state?.from?.pathname || "/login"
    }

    navigate(from, { replace: true })
}