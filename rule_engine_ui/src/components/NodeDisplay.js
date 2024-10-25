import React from 'react';

const NodeDisplay = ({ node }) => {
    return (
        <div className="node">
            <h5>Node Type: {node.type}</h5>
            <p>Value: {node.value}</p>
            <div className="children">
                {node.left && <NodeDisplay node={node.left} />}
                {node.right && <NodeDisplay node={node.right} />}
            </div>
        </div>
    );
};

export default NodeDisplay;
