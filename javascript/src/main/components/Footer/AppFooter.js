import React from "react";
const AppFooter = () => {
  const openInNewTab = (url) => {
    const newWindow = window.open(url, '_blank', 'noopener,noreferrer')
    if (newWindow) newWindow.opener = null
  }
  
  return (
    <footer className="bg-light p-3 text-center">
      This app is a class project of {" "}
      <a href="https://ucsb-cs156.github.io" target="_blank" rel="noreferrer noopener">
        CMPSC 156 
        </a> 
      {" "} at {" "}
      <a href="https://ucsb.edu" target="_blank" rel="noreferrer noopener">
        UCSB
      </a>
      . Check out the source code on {" "}
      <a href="https://github.com/ucsb-cs156-w21/proj-mapache-search" target="_blank" rel="noreferrer noopener">
        GitHub
      </a>
      !
    </footer>
  );
};

export default AppFooter;
