import React, { useState } from 'react';
import {
  Routes,
  Route,
  useNavigate
} from "react-router-dom";
import NewRequest from './components/new-request/new-request';
import RequestsDetail from './components/requests-detail/requests-detail';
import Requests from './components/requests/requests';
import { TabMenu } from 'primereact/tabmenu';

export const App = () => {
  const [title] = useState("");
  const [activeIndex, setActiveIndex] = useState((window.location.pathname === '/requests' || window.location.pathname === '/') ? 1 : 0);
  const navigate = useNavigate();

  const items = [
    { label: 'New Request', icon: 'pi pi-fw pi-save' },
    { label: 'Requests', icon: 'pi pi-fw pi-home' }
  ];

  return (
    <>
      <h2>{title}</h2>
      <TabMenu model={items} activeIndex={activeIndex} onTabChange={(e) => {
        setActiveIndex(e.index);
        if (e.index === 0) {
          navigate('/new-request');
        } else {
          navigate('/requests');
        }
      }} />
      <br />
      <Routes>
        <Route path="/" element={<Requests />} />
        <Route path="/requests" element={<Requests />} />
        <Route path="/requests/:id" element={<RequestsDetail />} />
        <Route path="/new-request" element={<NewRequest />} />
      </Routes>
    </>
  );
}

export default App;
