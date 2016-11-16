class WarBase(PyWarBase):
    def __init__(self):
        pass

    def action(self):
        global WA
        WA = self;
        return actionWarBase();


