import Swal from "sweetalert2";

export function notifyUser(titleMsg, bodyMsg, iconType, urlLocation){
 Swal.fire({
  title: `${titleMsg}`,
  html: `${bodyMsg}`,
  icon: `${iconType}`,
  timer: 3899,
  showConfirmButton: false,
  timerProgressBar: true,
 })

 setTimeout(()=>{
  if(urlLocation == 'reload'){
   window.location.reload()
  } else if(urlLocation == 'error'){
   console.log('error')
  }else{
   window.location.href = `${urlLocation}`;
  }
 }, 3899);
}