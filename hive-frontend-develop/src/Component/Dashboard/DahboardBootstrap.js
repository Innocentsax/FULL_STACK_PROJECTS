import data from '../data'
import "../Pages/CSS/dashboard.css";

const bootstrap = () => {
    return <>
        <div className='row justify-content-center align-item-center'>
            {data.cardData.map((item, index)=>{
            return(
                <div
                className="card p-12 overflow-hiden h-100 shadow"
                key={index}
                style={{
                  flex: '0 0 30%',
                  maxWidth: '30%',
                  minWidth: '30%',
                  marginBottom: '32px'
                }}
              >
                <div className="card-body my-card-body">
                    <h5 className="card-title">{item.title}</h5>
                    <p className="card-text">
                        <div className='new-page'>
                        Budget Rate <span>{item.budget}</span>
                        </div>
                       
                        </p>
                    <p className="card-text">
                        <div className='new-page'>
                        Job Type <span>{item.type}</span>
                        </div>
                    </p>
                    <p className="card-text">
                        <div className='new-page'>
                        Posted <span>{item.post}</span>
                        </div>
                    </p>
                    <p className="card-text">
                        <div className='new-page'>
                        Location <span>{item.location}</span>
                        </div>
                    </p>
                    <p className="card-text">
                        <div className='new-page'>
                        Location <span>{item.duration}</span>
                        </div>
                    </p>
                    <button className="btn btn-primary">View Task</button>
                </div>
                </div>
      )
    })}
  </div>
    </>
}
 
export default bootstrap;