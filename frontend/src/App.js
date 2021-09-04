import './App.css';
import { BrowserRouter as Router, Route } from "react-router-dom";

import GenerateUrl from "./pages/GenerateUrl.js";

function App() {
  return (
      <Router>
          <div>
              <Route path="/" exact component={GenerateUrl}/>
          </div>
      </Router>
  );
}

export default App;
