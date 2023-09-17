const monetize = (money) => {
    let strMoney = money.toString();
  
    const dot = strMoney.indexOf(".");
    let kobo = "";
  
    if (dot != -1) {
      kobo = strMoney.substring(strMoney.indexOf("."));
      strMoney = strMoney.substring(0, strMoney.indexOf("."));
    }
  
    const lenMoney = strMoney.length;
  
    if (lenMoney < 4) return money;
  
    const count = lenMoney % 3;
  
    let newStr = "";
  
    if (count != 0) {
      newStr = strMoney.substring(0, count);
      strMoney = strMoney.substring(count);
    }
  
    let parts = strMoney.match(/.{1,3}/g);
  
    let moneytized = parts.join(",");
  
    if (dot != -1) moneytized = `${moneytized}${kobo}`;
  
    return newStr != "" ? `${newStr},${moneytized}` : moneytized;
  };
  
  export { monetize };