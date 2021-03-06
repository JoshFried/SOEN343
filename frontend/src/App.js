/* eslint-disable react/jsx-filename-extension */
import React, { useState, useEffect, useMemo } from "react";
import { BrowserRouter as Router, Redirect, Route, Switch } from "react-router-dom";
import LoginForm from "./components/landing/LoginForm";
import RegistrationForm from "./components/landing/RegistrationForm";
import { AuthContext } from "./context/Auth";
import "./App.css";
import Navbar from "./components/tabs-navbar/Navbar";
import SHSPage from "./components/landing/SHSPage";
import SHCPage from "./components/landing/SHCPage";
import HouseUploadForm from "./components/HouseLayout/HouseUploadForm";
import { getAuthenticatedUser } from "./modules/UserProfileList/UserService";
import { HouseContext } from "./context/CurrentHouse";
import { useUserContext } from "./context/UserContext";
import HouseSelector from "./components/HouseSelector/HouseSelector";
import { getHouse, localStorageHouseID } from "./modules/HouseOverview/HouseService";
import { OutputDataContext } from "./context/OutputData";
import { getCurrentDate } from "./modules/HouseOverview/SimulationService";
import { DateContext } from "./context/DateContext";
import SHPPage from "./components/landing/SHPPage";
import SHHPage from "./components/landing/SHHPage";
const App = () => {
  // we will use this to get/fetch authentication token
  const [authTokens, setAuthTokens] = useState(
    localStorage.getItem("token") || ""
  );

  const [houseId, setHouseId] = useState(localStorage.getItem("houseID") || "")

  const [user, setUser] = useState(null);
  const [house, setHouse] = useState(null);
  const [outputData, setOutputData] = useState([
    { id: 1, date: new Date(), data: "This is a sample action log." },
  ]);
  const [currentDate, setCurrentDate] = useState(0)

  const userValue = useMemo(() => ({ user, setUser }), [user, setUser]);
  const houseValue = useMemo(() => ({ house, setHouse }), [house, setHouse]);
  const outputValue = useMemo(() => ({ outputData, setOutputData }), [outputData, setOutputData]);
  const currentDateValue = useMemo(() => ({ currentDate, setCurrentDate }), [currentDate, setCurrentDate]);

  const setTokens = (data) => {
    localStorage.setItem("token", JSON.stringify(data));
    setAuthTokens(data);
  };

  const loadUser = async () => {
    setUser(await getAuthenticatedUser());
  };

  useEffect(() => {
    if (!authTokens) {
      setUser(null)
    } else {
      loadUser()
    }
  }, [authTokens])

  useEffect(() => {
    const loadHouse = () => {
      try {
        getHouse(houseId).then(data => {
          setHouse(data)
          setHouseId(data.id)
          localStorageHouseID(data.id)
        })
      } catch (error) {
        localStorage.removeItem("houseID")
        setHouse(null)
      }
    }

    if (!houseId) {
      setHouse(null)
    } else {
      loadHouse()
    }
  }, [houseId])

  useEffect(() => {
    getCurrentDate().then(data=>{
      setCurrentDate(data)
    }).catch(error=>{
      alert(error)
    })
  }, [])

  return (
    <AuthContext.Provider value={{ authTokens, setAuthTokens: setTokens }}>
      <useUserContext.Provider value={userValue}>
        <HouseContext.Provider value={houseValue}>
          <OutputDataContext.Provider value={outputValue}>
            <DateContext.Provider value={currentDateValue}>
            <Router>
              <div>
                <Navbar authTokens={authTokens} />
                <Switch>
                  <Route exact path="/" render={() => user ? (house ? <SHSPage /> : <Redirect to="/upload" />) : <Redirect to="/login" />} />
                  <Route path="/register" render={() => user ? <Redirect to="/" /> : < RegistrationForm />} />
                  <Route path="/login" render={() => user ? <Redirect to="/" /> : <LoginForm />} />
                  <Route path="/shs" render={() => user ? (house ? <SHSPage /> : <Redirect to="/upload" />) : <Redirect to="/login" />} />
                  <Route path="/shc" render={() => user ? (house ? <SHCPage /> : <Redirect to="/upload" />) : <Redirect to="/login" />} />
                  <Route path="/upload" render={() => (user && house) ? <Redirect to="/" /> : <HouseUploadForm />} />
                  <Route path="/newUpload" render={() => user ? <HouseUploadForm /> : <Redirect to="login" />} />
                  <Route path="/houseSelect" render={() => user ? <HouseSelector /> : <Redirect to="/login" />} />
                  <Route path="/shp" render={() => user ? (house ? <SHPPage /> : <Redirect to="/upload" />) : <Redirect to="/login" />} />
                  <Route path="/shh" render={() => user ? (house ? <SHHPage/> : <Redirect to="/upload" />) : <Redirect to="/login" />} />
                </Switch>
              </div>
            </Router>
            </DateContext.Provider>
          </OutputDataContext.Provider>
        </HouseContext.Provider>
      </useUserContext.Provider>
    </AuthContext.Provider>
  );
};

export default App;
