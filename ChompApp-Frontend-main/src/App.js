import './App.css';
import Navbar from "./component/Navbar/Navbar";
import { BrowserRouter as Router } from "react-router-dom";
import AnimatedRoutes from './component/AnimatedRoutes';


function App() {

  return (
      <Router>
       <Navbar />
       <AnimatedRoutes />
      </Router>
  );
}

export default App