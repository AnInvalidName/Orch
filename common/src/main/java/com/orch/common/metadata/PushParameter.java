package com.orch.common.metadata;

import java.util.List;

public class PushParameter {
    private ParamReference SourceRef;
    private ParamReference TargetRef;

    public ParamReference getSourceRef() {
        return SourceRef;
    }

    public void setSourceRef(ParamReference sourceRef) {
        SourceRef = sourceRef;
    }

    public ParamReference getTargetRef() {
        return TargetRef;
    }

    public void setTargetRef(ParamReference targetRef) {
        TargetRef = targetRef;
    }
}
