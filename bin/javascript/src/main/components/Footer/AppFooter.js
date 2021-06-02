import React from "react";
export const space=" ";

const AppFooter = () => {
  return (
    <footer className="bg-light p-3 text-center">
      <p>
      This app is a class project of {" "}
      <a href="https://ucsb-cs156.github.io" target="_blank" rel="noreferrer noopener">
        CMPSC 156 
        </a> 
      {" "} at {" "}
      <a href="https://ucsb.edu" target="_blank" rel="noreferrer noopener">
        UCSB
      </a>
      . Check out the source code on {" "}
      <a href="https://github.com/ucsb-cs156-s21/proj-mapache-search" target="_blank" rel="noreferrer noopener">
        GitHub
      </a>
      .
      </p>
      <p>
        The cartoon Mapache images in the brand logo and magnifying glass favicon for this site were
        developed by Chelsea Lyon-Hayden, Art Director for UCSB Associate Students, and are
        used here by permission of the Executive Director of UCSB Associated Students.
        These images are Copyright © 2021 UCSB Associated Students, and may not be reused
        without express written permission of the Executive Director of UCSB Associated Students.  For more info, visit: 
        {space}
        <a href="https://www.as.ucsb.edu/sticker-packs/>">www.as.ucsb.edu/sticker-packs/</a>
        </p>

    </footer>
  );
};

export default AppFooter;
