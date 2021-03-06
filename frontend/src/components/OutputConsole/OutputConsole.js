import React, { useEffect } from 'react';
import './OutputConsole.css';
import { animateScroll } from "react-scroll";
import { useOutputData } from "../../context/OutputData";

const OutputConsole = (props) => {
  const { outputData } = useOutputData();

  const scrollToBottom = () => {
    animateScroll.scrollToBottom({
      containerId: "outputConsoleContainer"
    });
  }

  useEffect(() => {
    // Scroll to bottom of output console every time a new item is added
    scrollToBottom()
  });

  return (
    <div id="outputConsoleContainer">
      <h4 className="outputConsoleTitle">Output Console</h4>
      <ul className="outputConsoleList">
        {
          outputData.map(item => {
            return <li key={item.id} className="outputConsoleListItem">{`Log[${item.date.getHours()}:${item.date.getMinutes()}]: ${item.data}`}</li>
          })
        }
      </ul>
    </div>
  )
}

export default OutputConsole;