export function getInitials(words){
    words = words.toUpperCase();
    const wordsArr = words.split(" ");
    if(wordsArr.length>1){
      return wordsArr[0].charAt(0) + ""+ wordsArr[1].charAt(0);
    }
    return wordsArr[0].charAt(0) +wordsArr[0].charAt(1)
    
  }

  export function capitalizeFirstLetter(sentence){
    sentence= sentence.toLowerCase();
   let  sentenceArr  = sentence.split(" ");
   let sentenceCapitalize= sentenceArr.map(word => word.charAt(0).toUpperCase()+word.slice(1));
   return sentenceCapitalize.join(" ");
  }

  export function checkCredit(transactionType){
    if(transactionType=="DEBIT"){
        return "- ";
    }
    return "+";
  }
