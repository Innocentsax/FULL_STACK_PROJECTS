// export const authHeader = () => {

//     // const currentUser = store.getState().user
//     const jwtToken = localStorage.getItem("currentUser");


//     const HEADER = {
//         // "Content-Type" : "application/json",
//         // // authentication : currentUser?.authorization
//         // authorization: jwtToken
//         headers: {
//             "Content-Type": "application/json",
//             authorization: jwtToken,
//         },
//     }

//     console.log(Headers);
// }

// export const authHeader = () => {
//     const currentUser = store.getState().user;
//     return {
//       "Content-Type": "application/json",
//       authorization: currentUser?.confirmationToken,
//     };
// }