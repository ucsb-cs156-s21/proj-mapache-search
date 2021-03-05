describe('Jest Timezone', () => {
    it('should always be UTC', () => {
        // Because GitHub actions runs in UTC due to the slack API, changing jest to run in same timezone
        expect(new Date().getTimezoneOffset()).toBe(0);
    });
  });