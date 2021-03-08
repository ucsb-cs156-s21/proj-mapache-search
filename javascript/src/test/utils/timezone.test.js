describe('Jest Timezone', () => {
    it('should always be UTC', () => {
        // Because GitHub actions runs in UTC due to the slack API, changing jest to run in same timezone so that locally tests will pass
        expect(new Date().getTimezoneOffset()).toBe(0);
    });
  });