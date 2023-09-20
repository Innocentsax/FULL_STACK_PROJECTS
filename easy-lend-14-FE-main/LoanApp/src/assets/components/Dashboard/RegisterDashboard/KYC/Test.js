const url = "https://nodered.dipolediamond.com/payments/nipNameEnquiry";
const headers = {
  'Content-Type': 'application/json',
};

const data = {
  sessionID: "999001201011171444338881367575",
  destinationInstitutionCode: "333070",
  channelCode: "1",
  accountNumber: "5050007512"
};

const options = {
  method: 'POST',
  headers: headers,
  body: JSON.stringify(data), 
};

fetch(url, options)
  .then((response) => {
    return response.json();
  })
  .then((data) => {
    console.log(data);
  })
  .catch((error) => {
    console.error(error);
  });
