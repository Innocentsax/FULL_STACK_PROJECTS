import React, {useState} from 'react'

const AddTrainer = () => {
    const [username, setUsername] = useState("");
    const [description, setDescription] = useState("");
    const token = localStorage.getItem("token");

    async function sendAddTrainerRequest(e) {
        setDisabledButton(true);
        e.preventDefault();
        const reqBody = {
            username: username,
            description: description
        };

        console.log("ReqBody: " + reqBody);

        const url = 'https://fitnesso-app-new.herokuapp.com/person/admin/add_trainer';

        try {
            
            const response = await axios.post(url, reqBody, {
              headers: { Authorization: `Bearer ${token}` },
            });
            const res = response.data;
    
            console.log(res)
            alert("Trainer added successfully");
            setUsername(""); setDescription("");
      
        } catch (e) {
            console.log("Ensure all fields are filled correctly");
            alert("Ensure all fields are filled correctly");
           // console.log(e)
        }
    }

    const [disabledButton, setDisabledButton] = React.useState(false);

  return (
    <div className='admin-dashboard-board'>
        <div className="add-product-body">
        <form className="add-product-form">
          <div className="add-product-title">Trainer</div>
          <div className="add-product-subtitle">Add a Trainer</div>

          <div className="add-product-input-container add-product-ic1">
            <input
              name="productName"
              className="add-product-input"
              type="text"
              placeholder="Candidate's username"
              value={username}
              onChange={(event) => setUsername(event.target.value)}
              required
            />
          </div>
          
          <div className="add-product-textarea-container add-product-ic2">
            <textarea
              name="description"
              className="add-product-textarea"
              type="text"
              placeholder="Trainer Description "
              value={description}
              onChange={(event) => setDescription(event.target.value)}
              required
            />
          </div>

          <button type="submit" className="add-product-submit" onClick={sendAddTrainerRequest} >
            SUBMIT
          </button>
        </form>
      </div>
    </div>
  )
}

export default AddTrainer