
function dateDelivered() {
  var date = new Date();
  let dateDelivered = `${date.getFullYear()}-${
    date.getMonth().toString().length > 1
      ? date.getMonth()
      : `0${date.getMonth()}`
  }-${
    date.getDay().toString().length > 1
      ? date.getDay()
      : `0${date.getDay()}T${
          date.getHours().toString().length > 1
            ? date.getHours()
            : `0${date.getHours()}`
        }:${
          date.getMinutes().toString().length > 1
            ? date.getMinutes()
            : `0${date.getMinutes()}`
        }:${
          date.getMilliseconds().toString().length > 1
            ? date.getMilliseconds()
            : `0${date.getMilliseconds()}`
        }Z`
  }`;

  return dateDelivered;
}
export default dateDelivered;