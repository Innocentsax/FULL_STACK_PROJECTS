

export const  ImageContent = "./ImageContent";
export const frameImagePath = '../Images/fi_award.png'; // Adjust the path accordingly


export  const fetchContactInfo = async (location,config) => {
    try {
      const response = await fetch("http://localhost:8083/api/profile/get-profile", config);
      const data = await response.json();
    
  
      if (data.data.status === "CONTACT_UPDATED" && location.pathname !== "/kyc/2") {
        window.location.href = "/kyc/2"; // Redirect if status is CONTACT_UPDATED
      }
          else if(data.data.status=="EMPLOYMENT_UPDATED" && location.pathname !== "/kyc/3"){
     window.location.href="/kyc/3"
    
  }
  
  else if(data.data.status=="GOVERNMENT_UPDATED " && location.pathname !== "/kyc/4"){
    window.location.href="/kyc/4"
    
  }
  
  else if(data.data.status=="INCOME_UPDATED" && location.pathname !== "/kyc/5"){
    window.location.href="/kyc/5"
    
  }
      else if(data.data.status=="BANK_ACCOUNT_UPDATED" && location.pathname !== "/kyc/6"){
        window.location.href="/kyc/6"
  }
    } catch (error) {
      console.error("Error fetching contact data:", error);
    }
  };