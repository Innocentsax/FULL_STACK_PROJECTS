fetch("http://localhost:8999/api/v1/bank/account",{
    headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGlvcmx1amFja0BnbWFpbC5jb20iLCJpYXQiOjE2ODcyMTA0OTYsImV4cCI6MTY4NzI5Njg5Nn0.hl1lsx7hVz2p66cRLuOnsLenjAndqegysc42-MjCIcfPTV-qa2ivAHMy-N_9L9M9VU--x7J4lFJF5XhPep4SeQ`
      }
}).then(response=>response.json())
.then((data)=>{
    console.log(data)
})