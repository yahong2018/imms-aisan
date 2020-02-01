package com.zhxh.si.imms.kocheer.session;

import com.zhxh.si.imms.kocheer.command.Command_28;
import com.zhxh.si.imms.kocheer.domain.WorkstationSession;

public interface SessionStepService {
    Command_28 processSession(WorkstationSession session);
}
