import React, {Component} from 'react';
import NavBar from "./navbars/NavBar"
import AdminEventTable from "./AdminEventTable"
import LoginPage from "./LoginPage"
import NewEvent from "./NewEvent"
import UserEventTable from "./UserEventTable"
import Signup from "./Signup"
import Map from "./Map"
import SurveyDialog from "./dialogs/SurveyDialog"
import 'primeicons/package.json'
import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';
import "primeicons/primeicons.css"
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";

class App extends Component {
  state = {
    redirect: true
  }

  componentDidMount() {
    this.setState({
      redirect: false
    })
  }
  

  render (){

    return (
      <Router>
        {
          //this.state.redirect ? <Redirect push to="/newevent" /> : null
        }

        <Switch>
          <Route path="/survey">
            <SurveyDialog/>
          </Route>

          <Route path="/adminlist">
            <NavBar page = "adminpage"  />
            <AdminEventTable/>
          </Route>

          <Route path="/map">
            <Map/>
          </Route>

          <Route path="/signup">
            <NavBar page = "signup" />
            <Signup/>
          </Route>

          <Route path="/list">
            <NavBar page = "loggedin"  />
            <UserEventTable/>
          </Route>

          <Route path="/newevent">
            <NavBar page = "adminpage"  />
            <NewEvent/>
          </Route>

          <Route path="/login">
            <NavBar page = "login"  />
            <LoginPage />
          </Route>

          <Route path="/">
            <NavBar page = "login"  />
            <LoginPage />
          </Route>
        </Switch>

        

      </Router>
    );
    }
}

export default App;
