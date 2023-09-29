import { Link } from 'react-router-dom'
import "./page404.css"

const Page404 = () => {
  
  return (
    <section className="section-404">
        <div className="container-404">
            <h1 id="head">404</h1>
            <h3 id="sub-heading">PAGE NOT FOUND!</h3>
            <div className="div-404">
                <p id="message">The page you are looking might have been removed, had </p>
                <p id="message" className='other-text'>
                  its name changed  or is temporary unavailable.</p>
            </div>
            <Link to="/">
              <button className="home-btn">BACK TO HOME</button>
            </Link>
        </div>
    </section>
  )
}

export default Page404