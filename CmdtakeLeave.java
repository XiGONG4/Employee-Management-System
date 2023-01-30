import java.util.ArrayList;

public class CmdtakeLeave extends RecordedCommand {
    private Day leaveDate;
    private Day backDate;
    private String leaveType;
    private int leaveTime;
    private LeaveRecord l;
    private LeaveRecord l1;
    private Employee e;
    private ArrayList<String> activeHead = new ArrayList<>();
    private ArrayList<String> TargetTeam = new ArrayList<>();
    private String ExTargetTeam;
    private String ExactiveHead;
    private String mname;
    private Day dateSetup;
    // private int teamleadernum;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 5) {
                throw new ExInsufficientCommand();
            }
            Company co = Company.getInstance();
            mname = cmdParts[1];
            if (!co.checkduplicate(mname)) {
                throw new ExEmployeeNotFound();
            }
            e = co.getEmployee(mname);
            int teamleadernum = co.checkTeamLeaderNum(mname);
            leaveType = cmdParts[2];
            if (teamleadernum == 0) {// check whether the employee is a leader
                leaveDate = new Day(cmdParts[3]);
                backDate = new Day(cmdParts[4]);
                dateSetup = SystemDate.getInstance().clone();
                if (leaveDate.compareTo(dateSetup) < 0) {
                    throw new ExEarlierThanSystemtate();
                }
                leaveTime = 1;
                Day searching = leaveDate;
                while (searching.compareTo(backDate) != 0) {
                    searching = searching.next();
                    leaveTime++;
                    // if(searching == backDate){ break;}
                }
                // System.out.println(leaveTime);
                if (leaveTime < 7) {
                    if (leaveType.equals("BL")) {
                        throw new ExPleaseUseAL();
                    }
                }
                if (leaveTime >= 7) {
                    if (leaveType.equals("AL")) {
                        throw new ExPleaseUseBL();
                    }
                }
                // LeaveRecord l;
                // l = null;
                if (leaveType.equals("AL")) {
                    if (e.getremainingLeave() - leaveTime < 0) {
                        throw new ExInsufficientBalance();
                    }
                    if (e.getTakeBLorNot() == false) {
                        if (e.getremainingLeave() - leaveTime < 7) {
                            throw new ExTakeBLPlease();
                        }
                    }
                    e.addtotalLeave(leaveTime);
                    l = new LeaveRecord_AL(leaveDate, backDate);
                } else if (leaveType.equals("BL")) {
                    if (e.getremainingLeave() - leaveTime < 0) {
                        throw new ExInsufficientBalance();
                    }
                    l = new LeaveRecord_BL(leaveDate, backDate);
                    e.addtotalLeave(leaveTime);
                    e.setTakeBLorNot(true);
                } else if (leaveType.equals("NL")) {
                    l = new LeaveRecord_NL(leaveDate, backDate);
                } else if (leaveType.equals("SL")) {
                    if (e.checkSLexceed(leaveTime)) {
                        throw new ExInsufficientBalanceofSL();
                    }
                    e.addsickLeave(leaveTime);
                    l = new LeaveRecord_SL(leaveDate, backDate);

                } else
                    throw new ExInsufficientCommand();
                l1 = e.checkLeave(l);
                if (l1 != null) {
                    throw new ExLeaveOverlap();
                }
                l1 = e.checkActing(l);
                
                if (l1 != null) {
                    ExTargetTeam = co.actingHeadIn(mname,l1);
                    throw new ExCannotLeave();
                }
                co.takeLeave(mname, l);
/****************************** not leader case */
            } else {
                if (cmdParts.length < (5 + 2 * teamleadernum)) {
                    ArrayList<String> temp = e.getLeadingTeam();
                    for (String t : temp) {
                        boolean flag = true;
                        for (int idx = 0; idx < (cmdParts.length - 5) / 2; idx++) {
                            if (t.equals(cmdParts[5 + 2 * idx])) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            ExTargetTeam = t;
                            throw new ExMissingInput();
                        }
                    }
                }
                leaveDate = new Day(cmdParts[3]);
                backDate = new Day(cmdParts[4]);
                dateSetup = SystemDate.getInstance().clone();
                if (leaveDate.compareTo(dateSetup) < 0) {
                    throw new ExEarlierThanSystemtate();
                }
                leaveTime = 1;
                Day searching = leaveDate;
                while (searching.compareTo(backDate) != 0) {
                    searching = searching.next();
                    leaveTime++;
                    // if(searching == backDate){ break;}
                }
                // System.out.println(leaveTime);
                if (leaveTime < 7) {
                    if (leaveType.equals("BL")) {
                        throw new ExPleaseUseAL();
                    }
                }
                if (leaveTime >= 7) {
                    if (leaveType.equals("AL")) {
                        throw new ExPleaseUseBL();
                    }
                }
                // LeaveRecord l;
                // l = null;
                if (leaveType.equals("AL")) {
                    if (e.getremainingLeave() - leaveTime < 0) {
                        throw new ExInsufficientBalance();
                    }
                    if (e.getTakeBLorNot() == false) {
                        if (e.getremainingLeave() - leaveTime < 7) {
                            throw new ExTakeBLPlease();
                        }
                    }
                    e.addtotalLeave(leaveTime);
                    l = new LeaveRecord_AL(leaveDate, backDate);
                } else if (leaveType.equals("BL")) {
                    if (e.getremainingLeave() - leaveTime < 0) {
                        throw new ExInsufficientBalance();
                    }
                    l = new LeaveRecord_BL(leaveDate, backDate);
                    e.addtotalLeave(leaveTime);
                    e.setTakeBLorNot(true);
                } else if (leaveType.equals("NL")) {
                    l = new LeaveRecord_NL(leaveDate, backDate);
                } else if (leaveType.equals("SL")) {
                    if (e.checkSLexceed(leaveTime)) {
                        throw new ExInsufficientBalanceofSL();
                    }
                    e.addsickLeave(leaveTime);
                    l = new LeaveRecord_SL(leaveDate, backDate);

                } else
                    throw new ExInsufficientCommand();
                l1 = e.checkLeave(l);
                if (l1 != null) {
                    throw new ExLeaveOverlap();
                }
                for (int idx = 0; idx < teamleadernum; idx++) {
                    // System.out.println("loop reached");
                    String targetTeam = cmdParts[5 + 2 * idx];
                    String tempActiveHead = cmdParts[5 + 2 * idx + 1];
                    if (!co.checkTeamMemberDuplicate(targetTeam, tempActiveHead)) {
                        ExTargetTeam = targetTeam;
                        ExactiveHead = tempActiveHead;
                        throw new ExEmployeeNotFoundForTeam();
                    }
                    Employee etemp = co.getEmployee(tempActiveHead);
                    l1 = etemp.checkLeave(l);
                    if (l1 != null) {
                        ExactiveHead = tempActiveHead;
                        throw new ExActingHeadLeaving();
                    }
                }
                for (int idx = 0; idx < teamleadernum; idx++) {
                    // System.out.println("loop reached");
                    String targetTeam = cmdParts[5 + 2 * idx];
                    String tempActiveHead = cmdParts[5 + 2 * idx + 1];
                    co.setActiveHead(targetTeam, tempActiveHead, l);
                    activeHead.add(tempActiveHead);
                    TargetTeam.add(targetTeam);
                }
                co.takeLeave(mname, l);
            }
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExPleaseUseAL e1) {
            System.out.println(e1.getMessage());
        } catch (ExPleaseUseBL e1) {
            System.out.println(e1.getMessage());
        } catch (ExInsufficientBalance e1) {
            System.out.printf("%s %d days left only!\n", e1.getMessage(), e.getremainingLeave());
        } catch (ExTakeBLPlease e1) {
            System.out.printf(
                    "The annual leave is invalid.\nThe current balance is %d days only.\nThe employee has not taken any block leave yet.\nThe employee can take at most %d days of non-block annual leave\n",
                    e.getremainingLeave(), e.getremainingLeave() - 7);
            System.out.println(e1.getMessage());
        } catch (ExInsufficientBalanceofSL e1) {
            System.out.printf("Insufficient balance of sick leaves. %d days left only!\n", 135 - e.getsickLeave());
        } catch (ExLeaveOverlap e1) {
            System.out.printf("%sThe leave period %s is found!\n", e1.getMessage(), l1.toString());
        } catch (ExEarlierThanSystemtate e1) {
            System.out.printf("%s(%s)!\n", e1.getMessage(), dateSetup.toString());
        } catch (ExEmployeeNotFoundForTeam e1) {
            System.out.printf("Employee (%s) not found for %s!\n", ExactiveHead, ExTargetTeam);
        } catch (ExActingHeadLeaving e1) {
            System.out.printf("%s is on leave during %s!", ExactiveHead, l1.toString());
        } catch (ExMissingInput e1) {
            System.out.printf("Missing input:  Please give the name of the acting head for %s\n", ExTargetTeam);
        } catch (ExCannotLeave e1) {
            System.out.printf("Cannot take leave.  %s is the acting head of %s during %s!\n",mname,ExTargetTeam,l1.supertoString());
        }

    }

    @Override
    public void undoMe() {
        Company co = Company.getInstance();
        int teamleadernum = co.checkTeamLeaderNum(mname);
        if (teamleadernum == 0) {// e = co.getEmployee(mname);
            if (leaveType.equals("AL")) {
                e.subtotalLeave(leaveTime);
                // l = new LeaveRecord_AL(leaveDate, backDate);
            } else if (leaveType.equals("BL")) {
                // l = new LeaveRecord_BL(leaveDate, backDate);
                e.subtotalLeave(leaveTime);
                e.setTakeBLorNot(false);
            } else if (leaveType.equals("NL")) {
                // l = new LeaveRecord_NL(leaveDate, backDate);
            } else if (leaveType.equals("SL")) {
                e.subsickLeave(leaveTime);
                // l = new LeaveRecord_SL(leaveDate, backDate);

            }
            // e.removeLeave(l);
            co.removeLeave(mname, l);

        } else {
            if (leaveType.equals("AL")) {
                e.subtotalLeave(leaveTime);
                // l = new LeaveRecord_AL(leaveDate, backDate);
            } else if (leaveType.equals("BL")) {
                // l = new LeaveRecord_BL(leaveDate, backDate);
                e.subtotalLeave(leaveTime);
                e.setTakeBLorNot(false);
            } else if (leaveType.equals("NL")) {
                // l = new LeaveRecord_NL(leaveDate, backDate);
            } else if (leaveType.equals("SL")) {
                e.subsickLeave(leaveTime);
                // l = new LeaveRecord_SL(leaveDate, backDate);
            }
            for (int idx = 0; idx < teamleadernum; idx++) {
                // System.out.println("loop reached");
                String targetTeam = TargetTeam.get(idx);
                String tempActiveHead = activeHead.get(idx);
                co.removeActiveHead(targetTeam, tempActiveHead, l);
            }
            // TargetTeam.clear();
            // activeHead.clear();
            co.removeLeave(mname, l);
        }
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company co = Company.getInstance();
        // e = co.getEmployee(mname);
        int teamleadernum = co.checkTeamLeaderNum(mname);
        if (teamleadernum == 0) {
            if (leaveType.equals("AL")) {
                e.addtotalLeave(leaveTime);
                // l = new LeaveRecord_AL(leaveDate, backDate);
            } else if (leaveType.equals("BL")) {

                // l = new LeaveRecord_BL(leaveDate, backDate);
                e.addtotalLeave(leaveTime);
                e.setTakeBLorNot(true);
            } else if (leaveType.equals("NL")) {
                // l = new LeaveRecord_NL(leaveDate, backDate);
            } else if (leaveType.equals("SL")) {
                e.addsickLeave(leaveTime);
                // l = new LeaveRecord_SL(leaveDate, backDate);
            }
            // e.addLeaves(l);
            co.takeLeave(mname, l);
        } else {
            if (leaveType.equals("AL")) {
                e.addtotalLeave(leaveTime);
                // l = new LeaveRecord_AL(leaveDate, backDate);
            } else if (leaveType.equals("BL")) {

                // l = new LeaveRecord_BL(leaveDate, backDate);
                e.addtotalLeave(leaveTime);
                e.setTakeBLorNot(true);
            } else if (leaveType.equals("NL")) {
                // l = new LeaveRecord_NL(leaveDate, backDate);
            } else if (leaveType.equals("SL")) {
                e.addsickLeave(leaveTime);
                // l = new LeaveRecord_SL(leaveDate, backDate);
            }
            for (int idx = 0; idx < teamleadernum; idx++) {
                // System.out.println("loop reached");
                String targetTeam = TargetTeam.get(idx);
                String tempActiveHead = activeHead.get(idx);
                co.setActiveHead(targetTeam, tempActiveHead, l);
                // activeHead.add(tempActiveHead);
                // TargetTeam.add(targetTeam);
            }
            co.takeLeave(mname, l);
        }
        addUndoCommand(this);
    }

}