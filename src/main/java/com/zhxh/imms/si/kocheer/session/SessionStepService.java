package com.zhxh.imms.si.kocheer.session;

import com.zhxh.imms.si.kocheer.command.Command_28;
import com.zhxh.imms.si.kocheer.domain.WorkstationSession;

public interface SessionStepService {
    Command_28 processSession(WorkstationSession session);
}
