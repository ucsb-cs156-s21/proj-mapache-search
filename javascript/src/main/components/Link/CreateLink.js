import React from "react";

const CreateLink = (url) => {
    return (
        <a 
        href={url}
        target="_blank"
        >
            {url}
        </a>
    );
};
    
export default CreateLink;